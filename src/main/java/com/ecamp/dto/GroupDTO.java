package com.ecamp.dto;

import com.ecamp.dto.ChildDTO;

import java.util.List;

public class GroupDTO {
    public Long id;
    public String groupName;
    public List<ChildDTO> children;

    public GroupDTO(Long id, String groupName, List<ChildDTO> children) {
        this.id = id;
        this.groupName = groupName;
        this.children = children;
    }
}
