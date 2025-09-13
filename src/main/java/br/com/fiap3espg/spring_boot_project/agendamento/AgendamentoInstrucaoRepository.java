package br.com.fiap3espg.spring_boot_project.agendamento;

import br.com.fiap3espg.spring_boot_project.aluno.Aluno;
import br.com.fiap3espg.spring_boot_project.instrutor.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoInstrucaoRepository extends JpaRepository<AgendamentoInstrucao, Long> {
    
    @Query("SELECT a FROM AgendamentoInstrucao a WHERE a.instrutor = :instrutor AND a.dataHora = :dataHora AND a.status = 'AGENDADO'")
    List<AgendamentoInstrucao> findByInstrutorAndDataHora(@Param("instrutor") Instrutor instrutor, @Param("dataHora") LocalDateTime dataHora);
    
    @Query("SELECT COUNT(a) FROM AgendamentoInstrucao a WHERE a.aluno = :aluno AND DATE(a.dataHora) = DATE(:dataHora) AND a.status = 'AGENDADO'")
    long countByAlunoAndData(@Param("aluno") Aluno aluno, @Param("dataHora") LocalDateTime dataHora);
    
    @Query("SELECT a FROM AgendamentoInstrucao a WHERE a.dataHora = :dataHora AND a.status = 'AGENDADO'")
    List<AgendamentoInstrucao> findByDataHora(@Param("dataHora") LocalDateTime dataHora);
}
