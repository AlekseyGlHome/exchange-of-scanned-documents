package ru.mizer.edo.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(name = "doc_html", columnDefinition = "TEXT")
    private String docHtml;

    @Column(name = "doc_path", length = 250)
    private String docPath;

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

}