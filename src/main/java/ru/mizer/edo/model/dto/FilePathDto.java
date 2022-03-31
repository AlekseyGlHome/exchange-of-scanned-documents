package ru.mizer.edo.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.mizer.edo.model.entity.Document;

@Getter
@Setter
@Builder
public class FilePathDto {
    private Integer id;
    private DocumentDto doc;
    private String path;

    public String getPath(){
        return "/img_upload/"+path;
    }
}
