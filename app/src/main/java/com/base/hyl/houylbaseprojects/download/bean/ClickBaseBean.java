package com.base.hyl.houylbaseprojects.download.bean;

/**
 * 提供是否已经选择的处理
 */
public  class ClickBaseBean {
    /* 类似购物车 是否已选择 true 0 false 1*/
    private  int isSelected=1;//默认为 1 false

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected == 0;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected ? 0 : 1;
    }

    @Override
    public String toString() {
        return "AClickBaseBean{" +
                "isSelected=" + isSelected +
                '}';
    }
}
