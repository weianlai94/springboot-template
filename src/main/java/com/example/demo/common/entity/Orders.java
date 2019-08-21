package com.example.demo.common.entity;


import java.util.Date;
import javax.persistence.*;

public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 订单创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 订单修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 订单号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 订单总价格
     */
    @Column(name = "total_price")
    private Integer totalPrice;

    /**
     * 订单状态（订单状态（0-待支付 1-待发货 2-待收获 3-交易成功 4-交易关闭，5-退款中，6-退款成功，7-退款失败））
     */
    private Byte status;

    /**
     * 订单详细收获地址
     */
    private String address;

    /**
     * 发货时间
     */
    @Column(name = "deliver_time")
    private Date deliverTime;

    /**
     * 支付时间
     */
    @Column(name = "pay_time")
    private Date payTime;

    /**
     * 订单总运费
     */
    @Column(name = "total_freight")
    private Integer totalFreight;

    /**
     * 订单完成时间
     */
    @Column(name = "finish_time")
    private Date finishTime;

    /**
     * 是否删除（0：失效 1：有效）
     */
    @Column(name = "is_delete")
    private Byte isDelete;

    /**
     * 物流详细信息
     */
    @Column(name = "express_detail")
    private String expressDetail;

    /**
     * 收货时间
     */
    @Column(name = "receive_time")
    private Date receiveTime;

    /**
     *  收货人姓名
     */
    @Column(name = "receive_name")
    private String receiveName;

    /**
     * 自购返佣之和
     */
    @Column(name = "rate_total")
    private Integer rateTotal;

    /**
     * 退款金额
     */
    @Column(name = "refund_amount")
    private Integer refundAmount;

    /**
     * 订单取消时间
     */
    @Column(name = "cancel_time")
    private Date cancelTime;

    /**
     * 物流单号
     */
    @Column(name = "express_no")
    private String expressNo;

    /**
     * 支付金额
     */
    @Column(name = "pay_amount")
    private Integer payAmount;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取订单创建时间
     *
     * @return create_time - 订单创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置订单创建时间
     *
     * @param createTime 订单创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取订单修改时间
     *
     * @return modify_time - 订单修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置订单修改时间
     *
     * @param modifyTime 订单修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取订单号
     *
     * @return order_no - 订单号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置订单号
     *
     * @param orderNo 订单号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取用户手机号
     *
     * @return mobile - 用户手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置用户手机号
     *
     * @param mobile 用户手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取订单总价格
     *
     * @return total_price - 订单总价格
     */
    public Integer getTotalPrice() {
        return totalPrice;
    }

    /**
     * 设置订单总价格
     *
     * @param totalPrice 订单总价格
     */
    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * 获取订单状态（订单状态（0-待支付 1-待发货 2-待收获 3-交易成功 4-交易关闭，5-退款中，6-退款成功，7-退款失败））
     *
     * @return status - 订单状态（订单状态（0-待支付 1-待发货 2-待收获 3-交易成功 4-交易关闭，5-退款中，6-退款成功，7-退款失败））
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置订单状态（订单状态（0-待支付 1-待发货 2-待收获 3-交易成功 4-交易关闭，5-退款中，6-退款成功，7-退款失败））
     *
     * @param status 订单状态（订单状态（0-待支付 1-待发货 2-待收获 3-交易成功 4-交易关闭，5-退款中，6-退款成功，7-退款失败））
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取订单详细收获地址
     *
     * @return address - 订单详细收获地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置订单详细收获地址
     *
     * @param address 订单详细收获地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取发货时间
     *
     * @return deliver_time - 发货时间
     */
    public Date getDeliverTime() {
        return deliverTime;
    }

    /**
     * 设置发货时间
     *
     * @param deliverTime 发货时间
     */
    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    /**
     * 获取支付时间
     *
     * @return pay_time - 支付时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 设置支付时间
     *
     * @param payTime 支付时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 获取订单总运费
     *
     * @return total_freight - 订单总运费
     */
    public Integer getTotalFreight() {
        return totalFreight;
    }

    /**
     * 设置订单总运费
     *
     * @param totalFreight 订单总运费
     */
    public void setTotalFreight(Integer totalFreight) {
        this.totalFreight = totalFreight;
    }

    /**
     * 获取订单完成时间
     *
     * @return finish_time - 订单完成时间
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * 设置订单完成时间
     *
     * @param finishTime 订单完成时间
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * 获取是否删除（0：失效 1：有效）
     *
     * @return is_delete - 是否删除（0：失效 1：有效）
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除（0：失效 1：有效）
     *
     * @param isDelete 是否删除（0：失效 1：有效）
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取物流详细信息
     *
     * @return express_detail - 物流详细信息
     */
    public String getExpressDetail() {
        return expressDetail;
    }

    /**
     * 设置物流详细信息
     *
     * @param expressDetail 物流详细信息
     */
    public void setExpressDetail(String expressDetail) {
        this.expressDetail = expressDetail;
    }

    /**
     * 获取收货时间
     *
     * @return receive_time - 收货时间
     */
    public Date getReceiveTime() {
        return receiveTime;
    }

    /**
     * 设置收货时间
     *
     * @param receiveTime 收货时间
     */
    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    /**
     * 获取 收货人姓名
     *
     * @return receive_name -  收货人姓名
     */
    public String getReceiveName() {
        return receiveName;
    }

    /**
     * 设置 收货人姓名
     *
     * @param receiveName  收货人姓名
     */
    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    /**
     * 获取自购返佣之和
     *
     * @return rate_total - 自购返佣之和
     */
    public Integer getRateTotal() {
        return rateTotal;
    }

    /**
     * 设置自购返佣之和
     *
     * @param rateTotal 自购返佣之和
     */
    public void setRateTotal(Integer rateTotal) {
        this.rateTotal = rateTotal;
    }

    /**
     * 获取退款金额
     *
     * @return refund_amount - 退款金额
     */
    public Integer getRefundAmount() {
        return refundAmount;
    }

    /**
     * 设置退款金额
     *
     * @param refundAmount 退款金额
     */
    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount;
    }

    /**
     * 获取订单取消时间
     *
     * @return cancel_time - 订单取消时间
     */
    public Date getCancelTime() {
        return cancelTime;
    }

    /**
     * 设置订单取消时间
     *
     * @param cancelTime 订单取消时间
     */
    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    /**
     * 获取物流单号
     *
     * @return express_no - 物流单号
     */
    public String getExpressNo() {
        return expressNo;
    }

    /**
     * 设置物流单号
     *
     * @param expressNo 物流单号
     */
    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    /**
     * 获取支付金额
     *
     * @return pay_amount - 支付金额
     */
    public Integer getPayAmount() {
        return payAmount;
    }

    /**
     * 设置支付金额
     *
     * @param payAmount 支付金额
     */
    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }
}