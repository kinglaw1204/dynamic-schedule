package top.luozhou.test;

import lombok.Data;

/**
 * @description:
 * @author: luozhou
 * @create: 2019-10-16 17:33
 **/
@Data
public class Teacher {
    public Teacher(String name) {
        this.name = name;
    }

    private String name;
}
