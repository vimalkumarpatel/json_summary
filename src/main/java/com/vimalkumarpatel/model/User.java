package com.vimalkumarpatel.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private String guid;
    private boolean isActive;
    private String balance;
    private int age;
    private String eyeColor;
    private String name;
    private Gender gender;
    private String email;
    private String phone;
    private String address;
    private String registered;
    private Friend [] friends;
    private String greeting;
    private String favoriteFruit;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("guid", guid)
                .append("isActive", isActive)
                .append("balance", balance)
                .append("age", age)
                .append("eyeColor", eyeColor)
                .append("name", name)
                .append("gender", gender)
                .append("email", email)
                .append("phone", phone)
                .append("address", address)
                .append("registered", registered)
                .append("friends", friends)
                .append("greeting", greeting)
                .append("favoriteFruit", favoriteFruit)
                .toString();
    }
}
