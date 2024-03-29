package com.gvp.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Role implements Serializable {

	private Integer rid;

	private Boolean r01; // 用户管理

	private Boolean r02; // 添加报时表

	private Boolean r03; // 编辑报时表

	private Boolean r04; // 复制报时表

	private Boolean r05; // 删除报时表

	private Boolean r06; // 显示总价

	private Boolean r07; // 加工信息-加工单价

	private Boolean r08; // 辅助信息-原价

	private Boolean r09; // 密码修改

	private Boolean r10; // 显示图纸

	private Boolean r11; // 生成材料信息管理

	private Boolean r12; // 加工信息管理

	private Boolean r13; // 外发加工管理

	private Boolean r14; // 辅助信息管理

	private Boolean r15; // 参考信息管理

	private Boolean r16; // 图纸管理

	private Boolean r17; // 客户信息管理

	private Boolean r18; // 审核报时表

	private Boolean r19; // 材料种类规格管理

	private Boolean r20; // 原材料价格调节

	private Boolean r21; // 系统日志查看
	
	private Boolean r22; // 其他报价管理
	
	private Boolean r23; //产品编码信息
	
	
	private Boolean r24;//其他报价-单价
	private Boolean r25;//供应商材料单价
	private Boolean r26;//产品材料单价
	private Boolean r27;//辅助信息的加工单价
	private Boolean r28;//外发加工的加工单价
	private Boolean r29;//参考信息的运费
	private Boolean r30;//参考信息的减沙
	
	private Boolean r31;//供应商信息管理
	

	public Boolean getR31() {
		return r31;
	}

	public void setR31(Boolean r31) {
		this.r31 = r31;
	}

	public Boolean getR24() {
		return r24;
	}

	public void setR24(Boolean r24) {
		this.r24 = r24;
	}

	public Boolean getR25() {
		return r25;
	}

	public void setR25(Boolean r25) {
		this.r25 = r25;
	}

	public Boolean getR26() {
		return r26;
	}

	public void setR26(Boolean r26) {
		this.r26 = r26;
	}

	public Boolean getR27() {
		return r27;
	}

	public void setR27(Boolean r27) {
		this.r27 = r27;
	}

	public Boolean getR28() {
		return r28;
	}

	public void setR28(Boolean r28) {
		this.r28 = r28;
	}

	public Boolean getR29() {
		return r29;
	}

	public void setR29(Boolean r29) {
		this.r29 = r29;
	}

	public Boolean getR30() {
		return r30;
	}

	public void setR30(Boolean r30) {
		this.r30 = r30;
	}

	public Boolean getR23() {
		return r23;
	}

	public void setR23(Boolean r23) {
		this.r23 = r23;
	}

	public Boolean getR22() {
		return r22;
	}

	public void setR22(Boolean r22) {
		this.r22 = r22;
	}

	public Boolean getR09() {
		return r09;
	}

	public void setR09(Boolean r09) {
		this.r09 = r09;
	}

	public Role(Boolean flag) {
		super();
		this.r01 = flag;
		this.r02 = flag;
		this.r03 = flag;
		this.r04 = flag;
		this.r05 = flag;
		this.r06 = flag;
		this.r07 = flag;
		this.r08 = flag;
		this.r10 = flag;
		this.r11 = flag;
		this.r12 = flag;
		this.r13 = flag;
		this.r14 = flag;
		this.r15 = flag;
		this.r16 = flag;
		this.r17 = flag;
		this.r18 = flag;
		this.r19 = flag;
		this.r20 = flag;
		this.r21 = flag;
	}

	public Boolean getR03() {
		return r03;
	}

	public void setR03(Boolean r03) {
		this.r03 = r03;
	}

	public Boolean getR04() {
		return r04;
	}

	public void setR04(Boolean r04) {
		this.r04 = r04;
	}

	public Boolean getR05() {
		return r05;
	}

	public void setR05(Boolean r05) {
		this.r05 = r05;
	}

	public Boolean getR06() {
		return r06;
	}

	public void setR06(Boolean r06) {
		this.r06 = r06;
	}

	public Boolean getR07() {
		return r07;
	}

	public void setR07(Boolean r07) {
		this.r07 = r07;
	}

	public Role() {
	}

	public Role(Integer rid2) {
		this.rid = rid2;
	}

	public Boolean getR01() {
		return r01;
	}

	public void setR01(Boolean r01) {
		this.r01 = r01;
	}

	public Boolean getR02() {
		return r02;
	}

	public void setR02(Boolean r02) {
		this.r02 = r02;
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public Boolean getR08() {
		return r08;
	}

	public void setR08(Boolean r08) {
		this.r08 = r08;
	}

	public Boolean getR10() {
		return r10;
	}

	public void setR10(Boolean r10) {
		this.r10 = r10;
	}

	public Boolean getR11() {
		return r11;
	}

	public void setR11(Boolean r11) {
		this.r11 = r11;
	}

	public Boolean getR12() {
		return r12;
	}

	public void setR12(Boolean r12) {
		this.r12 = r12;
	}

	public Boolean getR13() {
		return r13;
	}

	public void setR13(Boolean r13) {
		this.r13 = r13;
	}

	public Boolean getR14() {
		return r14;
	}

	public void setR14(Boolean r14) {
		this.r14 = r14;
	}

	public Boolean getR15() {
		return r15;
	}

	public void setR15(Boolean r15) {
		this.r15 = r15;
	}

	public Boolean getR16() {
		return r16;
	}

	public void setR16(Boolean r16) {
		this.r16 = r16;
	}

	public Boolean getR17() {
		return r17;
	}

	public void setR17(Boolean r17) {
		this.r17 = r17;
	}

	public Boolean getR18() {
		return r18;
	}

	public void setR18(Boolean r18) {
		this.r18 = r18;
	}

	public Boolean getR19() {
		return r19;
	}

	public void setR19(Boolean r19) {
		this.r19 = r19;
	}

	public Boolean getR20() {
		return r20;
	}

	public void setR20(Boolean r20) {
		this.r20 = r20;
	}

	public Boolean getR21() {
		return r21;
	}

	public void setR21(Boolean r21) {
		this.r21 = r21;
	}

}
