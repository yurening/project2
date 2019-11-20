package com.cskaoyan.bean.mall.wx_order;

public class WxHandleOption {
    /**
     * cancel : true
     * delete : false
     * pay : true
     * comment : false
     * confirm : false
     * refund : false
     * rebuy : false
     */

    private boolean cancel;
    private boolean delete;
    private boolean pay;
    private boolean comment;
    private boolean confirm;
    private boolean refund;
    private boolean rebuy;

    public WxHandleOption() {
        this.cancel=false;
        this.delete=false;
        this.pay=false;
        this.comment=false;
        this.confirm=false;
        this.refund=false;
        this.rebuy=false;
    }

    public WxHandleOption(Short x){
        this();
        switch (x){
            case 101:
                setCancel(true);
                setPay(true);
                break;
            case 102:
                setRebuy(true);
                setDelete(true);
                break;
            case 103:
                break;
            case 201:
                setRefund(true);
                break;
            case 202:
                break;
            case 203:
                break;
            case 301:
                setRebuy(true);
                setConfirm(true);
                break;
            case 401:
                setDelete(true);
                setRebuy(true);
                setComment(true);
                break;
            case 402:
                break;
            default:
                break;
        }

    }
    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isPay() {
        return pay;
    }

    public void setPay(boolean pay) {
        this.pay = pay;
    }

    public boolean isComment() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public boolean isRefund() {
        return refund;
    }

    public void setRefund(boolean refund) {
        this.refund = refund;
    }

    public boolean isRebuy() {
        return rebuy;
    }

    public void setRebuy(boolean rebuy) {
        this.rebuy = rebuy;
    }

}
