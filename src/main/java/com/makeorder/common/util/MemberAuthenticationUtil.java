package com.makeorder.common.util;

import com.makeorder.member.dto.MemberDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class MemberAuthenticationUtil {

    public static Long getLoginMemberId() {
        MemberDetails details = (MemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return details.getMemberId();
    }
}
