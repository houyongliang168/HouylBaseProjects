package com.base.common.Bean;

import java.util.Comparator;

/**
 * Created by QunCheung on 2017/12/29.
 */

public class CompareUtils implements Comparator<Paramaters>{
    @Override
    public int compare(Paramaters o1, Paramaters o2) {
        if (o1.getKey().compareTo(o2.getKey()) > 0) {
            return 1;
        }else if (o1.getKey().compareTo(o2.getKey()) < 0){
            return -1;
        }else{
            return 0;
        }

    }
}
