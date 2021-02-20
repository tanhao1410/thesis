package com.github.tanhao1410.thesis.management.domain;

public class MonitoringRuleDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column monitoring_rule.id
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column monitoring_rule.name
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column monitoring_rule.method
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    private String method;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column monitoring_rule.level
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    private Integer level;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column monitoring_rule.interval
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    private Integer interval;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column monitoring_rule.need_mail
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    private Boolean needMail;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column monitoring_rule.is_alarm
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    private Boolean isAlarm;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column monitoring_rule.description
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    private String description;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column monitoring_rule.id
     *
     * @return the value of monitoring_rule.id
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column monitoring_rule.id
     *
     * @param id the value for monitoring_rule.id
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column monitoring_rule.name
     *
     * @return the value of monitoring_rule.name
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column monitoring_rule.name
     *
     * @param name the value for monitoring_rule.name
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column monitoring_rule.method
     *
     * @return the value of monitoring_rule.method
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    public String getMethod() {
        return method;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column monitoring_rule.method
     *
     * @param method the value for monitoring_rule.method
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column monitoring_rule.level
     *
     * @return the value of monitoring_rule.level
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column monitoring_rule.level
     *
     * @param level the value for monitoring_rule.level
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column monitoring_rule.interval
     *
     * @return the value of monitoring_rule.interval
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    public Integer getInterval() {
        return interval;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column monitoring_rule.interval
     *
     * @param interval the value for monitoring_rule.interval
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column monitoring_rule.need_mail
     *
     * @return the value of monitoring_rule.need_mail
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    public Boolean getNeedMail() {
        return needMail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column monitoring_rule.need_mail
     *
     * @param needMail the value for monitoring_rule.need_mail
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    public void setNeedMail(Boolean needMail) {
        this.needMail = needMail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column monitoring_rule.is_alarm
     *
     * @return the value of monitoring_rule.is_alarm
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    public Boolean getIsAlarm() {
        return isAlarm;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column monitoring_rule.is_alarm
     *
     * @param isAlarm the value for monitoring_rule.is_alarm
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    public void setIsAlarm(Boolean isAlarm) {
        this.isAlarm = isAlarm;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column monitoring_rule.description
     *
     * @return the value of monitoring_rule.description
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column monitoring_rule.description
     *
     * @param description the value for monitoring_rule.description
     *
     * @mbg.generated Sat Feb 20 15:15:50 CST 2021
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}