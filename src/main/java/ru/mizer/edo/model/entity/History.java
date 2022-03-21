package ru.mizer.edo.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Column(name = "old_doc_html", columnDefinition = "TEXT")
    private String oldDocHtml;

    @Column(name = "old_doc_path", length = 250)
    private String oldDocPath;

    @Column(name = "old_header", length = 250)
    private String oldHeader;

    @Column(name = "old_is_done")
    private Boolean oldIsDone;

    @Column(name = "new_doc_html", columnDefinition = "TEXT")
    private String newDocHtml;

    @Column(name = "new_doc_path", length = 250)
    private String newDocPath;

    @Column(name = "new_header", length = 250)
    private String newHeader;

    @Column(name = "new_is_done")
    private Boolean newIsDone;

    @Column(name = "event", nullable = false, length = 50)
    private String event;

}