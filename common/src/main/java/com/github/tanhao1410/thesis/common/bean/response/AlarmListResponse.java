package com.github.tanhao1410.thesis.common.bean.response;

import com.alibaba.fastjson.JSONObject;
import com.github.tanhao1410.thesis.common.domain.AlarmDO;
import com.github.tanhao1410.thesis.common.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @author tanhao
 * @date 2021/03/03 09:14
 */
@Getter
@Setter
public class AlarmListResponse extends JSONObject {


    @Getter
    @Setter
    static public class AlarmItem {


        public AlarmItem (AlarmDO alarmDO){
            this.alarmId = alarmDO.getId();
            this.itemName = alarmDO.getName();
            this.level = alarmDO.getLevel();
            ///正常情况下的level是0
            if (alarmDO.getIsNormal()){
                this.level = 0;
            }
            this.startTime = DateUtils.format(alarmDO.getStartTime(),null) ;
            this.value = alarmDO.getValue();
        }

        private Long alarmId;
        private String itemName;
        private Integer level;
        private String startTime;
        private String value;
    }



}
