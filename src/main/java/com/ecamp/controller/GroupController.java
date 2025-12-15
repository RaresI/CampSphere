package com.ecamp.controller;

import com.ecamp.dto.GroupDTO;
import com.ecamp.model.Camp;
import com.ecamp.model.Child;
import com.ecamp.model.Group;
import com.ecamp.model.Registration;
import com.ecamp.repository.GroupRepository;
import com.ecamp.repository.CampRepository;
import com.ecamp.repository.ChildRepository;
import com.ecamp.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ecamp.dto.ChildDTO;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired private GroupRepository repo;
    @Autowired private CampRepository campRepo;
    @Autowired private ChildRepository childRepo;
    @Autowired private RegistrationRepository regRepo;

    @GetMapping
    public List<Group> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Group getOne(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @GetMapping("/byCamp/{campId}")
    public List<GroupDTO> getByCamp(@PathVariable Long campId) {
        return repo.findAll().stream()
                .filter(g -> g.getCamp().getId().equals(campId))
                .map(group -> new GroupDTO(
                        group.getId(),                    // <-- now sending the ID
                        group.getName(),                  // group name
                        group.getChildren().stream()
                                .map(c -> new ChildDTO(
                                        c.getId(), 
                                        c.getName(), 
                                        c.getSchool(), 
                                        c.getParent().getId(),
                                        c.getDateOfBirth() != null ? c.getDateOfBirth().toString() : null,
                                        c.getMedicalInfo(),
                                        c.getEmail()
                                ))
                                .toList()
                ))
                .toList();
    }



    @PostMapping
    public Group create(@RequestBody Map<String,Object> b) {
        var g = new Group();
        g.setName(b.get("name").toString());
        g.setCamp(campRepo.findById(Long.valueOf(b.get("campId").toString())).orElseThrow());
        var children = ((List<Integer>)b.get("childIds")).stream()
                .map(i->childRepo.findById(i.longValue()).orElseThrow())
                .collect(Collectors.toList());
        g.setChildren(children);
        return repo.save(g);
    }



    @PostMapping("/generate/{campId}")
    public String generateGroups(@PathVariable Long campId) {
        Camp camp = campRepo.findById(campId).orElseThrow();


        List<Registration> regs = regRepo.findAll().stream()
                .filter(r -> r.getCamp().getId().equals(campId))
                .toList();

        Map<String, List<Child>> bySchool = regs.stream()
                .map(Registration::getChild)
                .collect(Collectors.groupingBy(Child::getSchool));

        int groupCounter = 1;

        for (var entry : bySchool.entrySet()) {
            List<Child> children = new java.util.ArrayList<>(entry.getValue());

            while (children.size() >= 5) {
                List<Child> groupMembers = children.subList(0, 5);
                Group g = new Group();
                g.setCamp(camp);
                g.setName("Team " + groupCounter++);
                g.setChildren(new ArrayList<>(groupMembers));  // ✅ FIX HERE
                g = repo.save(g);
                for (Child c : groupMembers) {
                    c.setGroup(g);
                    childRepo.save(c);
                }
                children.subList(0, 5).clear();
            }


            if (children.size() == 4 || children.size() == 3) {
                Group g = new Group();
                g.setCamp(camp);
                g.setName("Team " + groupCounter++);
                g.setChildren(children);
                g = repo.save(g);
                for (Child c : children) {
                    c.setGroup(g);
                    childRepo.save(c);
                }
                children.clear();
            }

            if (!children.isEmpty()) {
                Group g = new Group();
                g.setCamp(camp);
                g.setName("Team " + groupCounter++ + " (small)");
                g.setChildren(children);
                g = repo.save(g);
                for (Child c : children) {
                    c.setGroup(g);
                    childRepo.save(c);
                }

                children.clear();
            }
        }

        return "Groups generated successfully for camp " + camp.getName();
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
