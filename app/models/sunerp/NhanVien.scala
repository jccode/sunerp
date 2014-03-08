package models.sunerp

import models.core.{AbstractQuery, AbstractTable, WithId}
import play.api.db.slick.Config.driver.simple._
import play.api.data.Form
import play.api.data.Forms._

/**
 * The Class NhanVien.
 *
 * @author Nguyen Duc Dung
 * @since 3/4/14 9:56 AM
 *
 */
case class NhanVien(
                     id: Option[Long] = None,
                     firstName: String,
                     lastName: String,
                     heSoLuong: Long,
                     chucVuId: Long,
                     phongBangId: Long
                     ) extends WithId[Long]

class NhanViens(tag: Tag) extends AbstractTable[NhanVien](tag, "nhan_vien") {

  def firstName = column[String]("firstName", O.NotNull)

  def lastName = column[String]("lastName", O.NotNull)

  def heSoLuong = column[Long]("heSoLuong", O.NotNull)

  def chucVuId = column[Long]("chucVuId", O.NotNull)

  def chucVu = foreignKey("chuc_vu_nhan_vien_fk", chucVuId, ChucVus)(_.id)

  def phongBangId = column[Long]("phongBangId", O.NotNull)

  def phongBang = foreignKey("phong_bang_nhan_vien_fk", phongBangId, PhongBangs)(_.id)

  def * = (id.?, firstName, lastName, heSoLuong, chucVuId, phongBangId) <>(NhanVien.tupled, NhanVien.unapply)
}

object NhanViens extends AbstractQuery[NhanVien, NhanViens](new NhanViens(_)) {

  def editForm = Form(
    mapping(
      "id" -> optional(longNumber),
      "firstName" -> text(minLength = 4),
      "lastName" -> text(minLength = 4),
      "heSoLuong" -> longNumber,
      "chucVuId" -> longNumber,
      "phongBangId" -> longNumber
    )(NhanVien.apply)(NhanVien.unapply)
  )

}