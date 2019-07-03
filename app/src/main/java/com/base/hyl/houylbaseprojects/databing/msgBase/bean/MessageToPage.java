package com.base.hyl.houylbaseprojects.databing.msgBase.bean;

public class MessageToPage {
    private boolean toApprovePage;

    public boolean isToApprovePage() {
        return toApprovePage;
    }

    public void setToApprovePage(boolean toApprovePage) {
        this.toApprovePage = toApprovePage;
    }

    @Override
    public String toString() {
        return "MessageToPage{" +
                "toApprovePage=" + toApprovePage +
                '}';
    }
}
