package com.example.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("bms_billboard")
public class BillBoard {
    //这个是自增了;
    @TableId(type = IdType.AUTO)
    private int id;
    @TableField("content")
    private String content;
    private String create_time;
    private int show;
}



