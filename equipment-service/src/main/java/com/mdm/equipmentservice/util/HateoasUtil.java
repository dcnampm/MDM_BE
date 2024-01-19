package com.mdm.equipmentservice.util;


import org.springframework.hateoas.Link;

public class HateoasUtil {
    /**
     * Get the path of the link
     * Ex: http://localhost:8000/api/v1/equipments => /api/v1/equipments
     * @param link
     * @return
     */
    public static Link pathOnly(Link link) {
        return Link.of(link.toUri().getPath(), link.getRel());
    }
}
