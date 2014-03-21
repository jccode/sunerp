package models.sunerp

import models.core.{AbstractQuery, AbstractTable, WithId}
import play.api.db.slick.Config.driver.simple._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import dtos.{NhanVienDto, ExtGirdDto, PagingDto}

/**
 * The Class NhanVien.
 *
 * @author Nguyen Duc Dung
 * @since 3/4/14 9:56 AM
 *
 */
case class NhanVien(
                     id: Option[Long] = None,
                     maNv: String,
                     firstName: String,
                     lastName: String,
                     heSoLuong: Long,
                     chucVuId: Long,
                     phongBangId: Long
                     ) extends WithId[Long]

class NhanViens(tag: Tag) extends AbstractTable[NhanVien](tag, "nhanVien") {

  def maNv = column[String]("maNv", O.NotNull)

  def firstName = column[String]("firstName", O.NotNull)

  def lastName = column[String]("lastName", O.NotNull)

  def heSoLuong = column[Long]("heSoLuong", O.NotNull)

  def chucVuId = column[Long]("chucVuId", O.NotNull)

  def chucVu = foreignKey("chuc_vu_nhan_vien_fk", chucVuId, ChucVus)(_.id)

  def phongBangId = column[Long]("phongBangId", O.NotNull)

  def phongBang = foreignKey("phong_bang_nhan_vien_fk", phongBangId, PhongBangs)(_.id)

  def idx = index("nhanvien_index", maNv, unique = true)

  def * = (id.?, maNv, firstName, lastName, heSoLuong, chucVuId, phongBangId) <>(NhanVien.tupled, NhanVien.unapply)
}

object NhanViens extends AbstractQuery[NhanVien, NhanViens](new NhanViens(_)) {

  def editForm = Form(
    mapping(
      "id" -> optional(longNumber),
      "maNv" -> text(minLength = 4),
      "firstName" -> text(minLength = 4),
      "lastName" -> text(minLength = 4),
      "heSoLuong" -> longNumber,
      "chucVuId" -> longNumber,
      "phongBangId" -> longNumber
    )(NhanVien.apply)(NhanVien.unapply)
  )

  implicit val nhanVienJsonFormat = Json.format[NhanVien]

  def load(pagingDto: PagingDto)(implicit session: Session): ExtGirdDto[NhanVienDto] = {
    var query = for (
      nhanVien <- this;
      chucVu <- nhanVien.chucVu;
      phongBang <- nhanVien.phongBang
    ) yield (nhanVien, chucVu, phongBang)

    pagingDto.filters.foreach(filter => {
      query = query.where(table => {
        val (nhanVien, chucVu, phongBang) = table
        filter.property match {
          case "firstName" => nhanVien.firstName.toLowerCase like filter.valueForLike
          case _ => throw new Exception("Invalid filtering key: " + filter.property)
        }
      })
    })

    pagingDto.sorts.foreach(sort => {
      query = query.sortBy(table => {
        val (nhanVien, chucVu, phongBang) = table
        sort.property match {
          case "maNv" => orderColumn(sort.direction, nhanVien.maNv)
          case "firstName" => orderColumn(sort.direction, nhanVien.firstName)
          case "lastName" => orderColumn(sort.direction, nhanVien.lastName)
          case "heSoLuong" => orderColumn(sort.direction, nhanVien.heSoLuong)
          case "chucVu.name" => orderColumn(sort.direction, chucVu.name)
          case "phongBang.name" => orderColumn(sort.direction, phongBang.name)
          case _ => throw new Exception("Invalid sorting key: " + sort.property)
        }
      })
    })

    val totalRow = Query(query.length).first()

    val rows = query
      .drop(pagingDto.start)
      .take(pagingDto.limit)
      .list
      .map(NhanVienDto.apply)

    ExtGirdDto[NhanVienDto](
      total = totalRow,
      data = rows
    )
  }
}