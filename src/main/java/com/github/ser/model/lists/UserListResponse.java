package com.github.ser.model.lists;

import com.github.ser.enums.Role;
import com.github.ser.model.database.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Builder
@Data
public class UserListResponse {
    private List<User> users;
    private int count;
}
