package ru.wdsoft.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

import ru.wdsoft.api.ApiSerializable;

/**
 * Created by slaventii@mail.ru on 22.02.2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiFile extends ApiSerializable {

    /**
     * Для логирования
     */
    private final String LOG_PREFIX = this.getClass().getName() + " -- ";

    @JsonProperty("FileId")
    private UUID FileId;

    @JsonProperty("DocumentTypeId")
    private UUID DocumentTypeId;

    @JsonProperty("DocumentTypeName")
    private String DocumentTypeName;

    @JsonIgnore
    public String getFileId() {
        return fromJsonProp(FileId);
    }
    @JsonIgnore
    public void setFileId(String fileId) {
        FileId = toUUIDJsonProp(fileId);
    }
    @JsonIgnore
    public String getDocumentTypeId() {
        return fromJsonProp(DocumentTypeId);
    }
    @JsonIgnore
    public void setDocumentTypeId(String documentTypeId) {
        DocumentTypeId = toUUIDJsonProp(documentTypeId);
    }
    @JsonIgnore
    public String getDocumentTypeName() {
        return fromJsonProp(DocumentTypeName);
    }
    @JsonIgnore
    public void setDocumentTypeName(String documentTypeName) {
        DocumentTypeName = toJsonProp(documentTypeName);
    }
}
