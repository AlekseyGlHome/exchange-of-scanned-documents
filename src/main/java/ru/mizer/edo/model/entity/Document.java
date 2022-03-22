package ru.mizer.edo.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @Column(name = "date_doc")
    private LocalDateTime dateDoc;

    @Column(name = "sum")
    private Double sum;

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "doc_html", columnDefinition = "TEXT")
    private String docHtml;

    @Column(name = "header", nullable = false, length = 250)
    private String header;

    @Column(name = "is_done", nullable = false)
    private Boolean isDone;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autor_id", nullable = false)
    private User autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_last_change")
    private User userLastChange;

    @Column(name = "date_last_edit")
    private LocalDateTime dateLastEdit;
}