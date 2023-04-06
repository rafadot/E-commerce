package com.ecommerce.Ecommerce.V1.util;

import com.ecommerce.Ecommerce.V1.model.Role;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountUtil {
    public static boolean containRole(Set<Role> roles , String rolesRequest){
        String[] contain = rolesRequest.split(",");
        List<String> roleList = roles
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        for(String roleForRoleList : roleList){
            for (String s : contain) {
                if (s.equals(roleForRoleList))
                    return true;
            }
        }
        return false;
    }
}
