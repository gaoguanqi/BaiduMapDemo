package com.maple.baidu.demo.doubles;

public class ItemBean {
    private boolean hasSelected;
    private String txt;

    public ItemBean(boolean hasSelected, String txt) {
        this.hasSelected = hasSelected;
        this.txt = txt;
    }

    public boolean isHasSelected() {
        return hasSelected;
    }

    public void setHasSelected(boolean hasSelected) {
        this.hasSelected = hasSelected;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
