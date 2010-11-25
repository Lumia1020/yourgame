package com.tiema.golf.club.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tiema.core.constant.State;

/**
 * @ClassName: GolfClub
 * @Description: 高尔夫俱乐部实体
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 18, 2010 3:15:07 PM
 * 
 */
@Entity
@Table(name = "T_GOLF_CLUB")
public class GolfClub implements Serializable {

	private static final long	serialVersionUID	= 1718815078668833235L;

	/** @Fields id : 俱乐部id */
	private Long				id;

	/** @Fields clubName : 俱乐部名称 */
	private String				clubName;

	/** @Fields contact : 联系人 */
	private String				contact;

	/** @Fields telephone : 固定电话 */
	private String				telephone;

	/** @Fields fax : 传真号码 */
	private String				fax;

	/** @Fields address : 俱乐部地址 */
	private String				address;

	/** @Fields zipCode : 邮政编码 */
	private String				zipCode;

	/** @Fields im : QQ/MSN */
	private String				im;

	/** @Fields url : 球会网址 */
	private String				url;

	/** @Fields route : 交通线路 */
	private String				route;

	/** @Fields state : 俱乐部状态:1允许进行订场,2为不允许 */
	private State				state;

	/** @Fields remark : 备注 */
	private String				remark;

	public GolfClub() {
	}

	public GolfClub(Long golfClubId) {
		this.id = golfClubId;
	}

	@Column(length = 200)
	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	@Column(length = 150)
	public String getAddress() {
		return address;
	}

	@Column(length = 100)
	public String getClubName() {
		return clubName;
	}

	@Column(length = 50)
	public String getContact() {
		return contact;
	}

	@Column(length = 400)
	public String getUrl() {
		return url;
	}

	@Id
	@SequenceGenerator(name = "GOLF_CLUB_SEQ_GEN", sequenceName = "T_GOLF_CLUB_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GOLF_CLUB_SEQ_GEN")
	public Long getId() {
		return id;
	}

	@Column(length = 50)
	public String getIm() {
		return im;
	}

	@Column(length = 50)
	public String getFax() {
		return fax;
	}

	@Column(length = 400)
	public String getRemark() {
		return remark;
	}

	@Column(nullable = false)
	@Enumerated
	public State getState() {
		return state;
	}

	@Column(length = 50)
	public String getTelephone() {
		return telephone;
	}

	@Column(length = 6)
	public String getZipCode() {
		return zipCode;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIm(String im) {
		this.im = im;
	}

	public void setFax(String mobile) {
		this.fax = mobile;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}
