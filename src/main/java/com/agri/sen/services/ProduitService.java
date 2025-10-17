package com.agri.sen.services;
import com.webgram.stage.entity.enums.StatutType;
import com.webgram.stage.model.AgentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public interface AgentService {

    AgentDTO createAgent(AgentDTO agentDTO);
    AgentDTO updateAgent(AgentDTO agentDTO);
    void deleteAgent(Long id);
    AgentDTO getAgent(Long id);
    Page<AgentDTO> getAllAgents(Map<String, String> searchParams, Pageable pageable);

    void exportAgent(PrintWriter writer);
    //pour avoir de l argent faut travailler

    List<AgentDTO> importAgents(List<AgentDTO> agents);
    AgentDTO updateStatut(Long id, StatutType statutType);

}
