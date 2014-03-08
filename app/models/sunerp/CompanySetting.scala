package models.sunerp

import models.core.{AbstractQuery, AbstractTable, WithId}
import play.api.db.slick.Config.driver.simple._
import play.api.data.Form
import play.api.data.Forms._

/**
 * The Class CompanySetting.
 *
 * @author Nguyen Duc Dung
 * @since 3/4/14 10:01 AM
 *
 */
case class CompanySetting(
                           id: Option[Long] = None,
                           companyId: Long,
                           luongToiThieu: Long
                           ) extends WithId[Long]

class CompanySettings(tag: Tag) extends AbstractTable[CompanySetting](tag, "company_setting") {

  def companyId = column[Long]("companyId", O.NotNull)

  def company = foreignKey("company_company_setting_fk", companyId, Companies)(_.id)

  def luongToiThieu = column[Long]("luongToiThieu", O.NotNull)

  def * = (id.?, companyId, luongToiThieu) <>(CompanySetting.tupled, CompanySetting.unapply)
}

object CompanySettings extends AbstractQuery[CompanySetting, CompanySettings](new CompanySettings(_)) {

  def editForm = Form(
    mapping(
      "id" -> optional(longNumber),
      "companyId" -> longNumber,
      "luongToiThieu" -> longNumber
    )(CompanySetting.apply)(CompanySetting.unapply)
  )

}