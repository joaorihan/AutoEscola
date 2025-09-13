CREATE TABLE agendamentos_instrucao(

    id bigint NOT NULL AUTO_INCREMENT,
    aluno_id bigint NOT NULL,
    instrutor_id bigint,
    data_hora datetime NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'AGENDADO',
    motivo_cancelamento VARCHAR(255),

    PRIMARY KEY(id),
    FOREIGN KEY (aluno_id) REFERENCES alunos(id),
    FOREIGN KEY (instrutor_id) REFERENCES instrutores(id)

)
