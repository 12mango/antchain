package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

public class Activity implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.id
     *
     * @mbggenerated
     */
    @TableId(type= IdType.AUTO)
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.aid
     *
     * @mbggenerated
     */
    private Integer aid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.target
     *
     * @mbggenerated
     */
    private Double target;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.now
     *
     * @mbggenerated
     */
    private Double now;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.start
     *
     * @mbggenerated
     */
    private Date start;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.end
     *
     * @mbggenerated
     */
    private Date end;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.topic
     *
     * @mbggenerated
     */
    private String topic;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.director
     *
     * @mbggenerated
     */
    private String director;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.contact
     *
     * @mbggenerated
     */
    private String contact;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.description
     *
     * @mbggenerated
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.url
     *
     * @mbggenerated
     */
    private String url;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table activity
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.id
     *
     * @return the value of activity.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.id
     *
     * @param id the value for activity.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.aid
     *
     * @return the value of activity.aid
     *
     * @mbggenerated
     */
    public Integer getAid() {
        return aid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.aid
     *
     * @param aid the value for activity.aid
     *
     * @mbggenerated
     */
    public void setAid(Integer aid) {
        this.aid = aid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.target
     *
     * @return the value of activity.target
     *
     * @mbggenerated
     */
    public Double getTarget() {
        return target;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.target
     *
     * @param target the value for activity.target
     *
     * @mbggenerated
     */
    public void setTarget(Double target) {
        this.target = target;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.now
     *
     * @return the value of activity.now
     *
     * @mbggenerated
     */
    public Double getNow() {
        return now;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.now
     *
     * @param now the value for activity.now
     *
     * @mbggenerated
     */
    public void setNow(Double now) {
        this.now = now;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.start
     *
     * @return the value of activity.start
     *
     * @mbggenerated
     */
    public Date getStart() {
        return start;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.start
     *
     * @param start the value for activity.start
     *
     * @mbggenerated
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.end
     *
     * @return the value of activity.end
     *
     * @mbggenerated
     */
    public Date getEnd() {
        return end;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.end
     *
     * @param end the value for activity.end
     *
     * @mbggenerated
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.topic
     *
     * @return the value of activity.topic
     *
     * @mbggenerated
     */
    public String getTopic() {
        return topic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.topic
     *
     * @param topic the value for activity.topic
     *
     * @mbggenerated
     */
    public void setTopic(String topic) {
        this.topic = topic == null ? null : topic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.director
     *
     * @return the value of activity.director
     *
     * @mbggenerated
     */
    public String getDirector() {
        return director;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.director
     *
     * @param director the value for activity.director
     *
     * @mbggenerated
     */
    public void setDirector(String director) {
        this.director = director == null ? null : director.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.contact
     *
     * @return the value of activity.contact
     *
     * @mbggenerated
     */
    public String getContact() {
        return contact;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.contact
     *
     * @param contact the value for activity.contact
     *
     * @mbggenerated
     */
    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.description
     *
     * @return the value of activity.description
     *
     * @mbggenerated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.description
     *
     * @param description the value for activity.description
     *
     * @mbggenerated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.url
     *
     * @return the value of activity.url
     *
     * @mbggenerated
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.url
     *
     * @param url the value for activity.url
     *
     * @mbggenerated
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", aid=").append(aid);
        sb.append(", target=").append(target);
        sb.append(", now=").append(now);
        sb.append(", start=").append(start);
        sb.append(", end=").append(end);
        sb.append(", topic=").append(topic);
        sb.append(", director=").append(director);
        sb.append(", contact=").append(contact);
        sb.append(", description=").append(description);
        sb.append(", url=").append(url);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}