package controllers

import controllers.element.{MainTemplate, BaseCtr}
import models.sunerp.{PhongBans, PhongBan}
import models.core.AbstractQuery
import play.api.libs.json.{Json, JsValue, Writes}
import dtos.{PhongBanDto, PagingDto}
import play.api.db.slick.Session
import jp.t2v.lab.play2.stackc.RequestWithAttributes
import play.api.mvc.AnyContent
import com.escalatesoft.subcut.inject.BindingModule

/**
 * The Class PhongBanCtr.
 *
 * @author Nguyen Duc Dung
 * @since 3/8/14 4:19 PM
 *
 */
class PhongBanCtr(implicit val bindingModule: BindingModule) extends BaseCtr[PhongBan, PhongBans] with MainTemplate {
  override def editForm(implicit session: Session) = PhongBans.editForm

  override implicit val jsonWrite: Writes[PhongBan] = PhongBans.phongBanJsonFormat
  override val dao: AbstractQuery[PhongBan, PhongBans] = PhongBans
  override val domainName: String = DomainKey.phongBan

  override protected def doIndex(paging: PagingDto, request: RequestWithAttributes[AnyContent])(implicit session: Session): JsValue = {
    implicit val jsonWrite = PhongBanDto.jsonWrite
    val result = PhongBans.load(paging)
    Json.toJson(result)
  }
}
