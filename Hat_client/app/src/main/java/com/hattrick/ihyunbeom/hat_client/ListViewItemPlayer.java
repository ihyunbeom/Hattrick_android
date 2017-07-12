package com.hattrick.ihyunbeom.hat_client;

/**
 * Created by HyunBum on 2017. 7. 11..
 */

public class ListViewItemPlayer {

    private String txtname;
    private String txtposition;
    private String txtgoal;
    private String txtouting;

    public void setName(String name) {
        txtname = name ;
    }
    public void setPosition(String position){
        txtposition = position;
    }
    public void setGoal(int goal){ txtgoal = Integer.toString(goal); }
    public void setOuting(int outing){
        txtouting = Integer.toString(outing);
    }

    public String getName() {
        return this.txtname ;
    }
    public String getPosition() {
        return this.txtposition ;
    }
    public String getGoal() {
        return this.txtgoal ;
    }
    public String getOuting() {
        return this.txtouting ;
    }

}
