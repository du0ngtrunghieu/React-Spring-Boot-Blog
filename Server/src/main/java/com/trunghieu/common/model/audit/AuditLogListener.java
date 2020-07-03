package com.trunghieu.common.model.audit;


import com.trunghieu.api.audit.AuditLogService;
import com.trunghieu.common.dto.AuditTrailDto;
import com.trunghieu.common.util.ApplicationContextProvider;
import com.trunghieu.common.util.BlogENUM;
import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/6/2020.
 * Class: AuditLogListener.java
 * By : Admin
 */
@Component
public class AuditLogListener implements PostInsertEventListener, PostUpdateEventListener, PostDeleteEventListener {
    @Override
    public void onPostInsert(PostInsertEvent event) {
        Object entity = event.getEntity();
        if (entity instanceof AuditAware) {
            List<AuditTrailDto> auditTrailDTOList = new ArrayList<>();
            AuditLogService auditLogService = (AuditLogService) ApplicationContextProvider.getApplicationContext().getBean("auditLogService");
            String[] propertyNames = event.getPersister().getPropertyNames();
            Object[] states = event.getState();
            for (int i = 0; i < propertyNames.length; i++) {
                System.out.println("Inside On Save   ************    ************** ===>>>      " + propertyNames[i]);
                if(states[i] != null){
                    auditTrailDTOList.add(new AuditTrailDto(entity.getClass().getCanonicalName(), event.getId().toString(), BlogENUM.AuditEvent.INSERT.name(), propertyNames[i],null, states[i].toString()));
                }

            }
            auditTrailDTOList.forEach(auditLogService::save);
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        return false;
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        Object entity = event.getEntity();
        if (entity instanceof AuditAware) {
            String[] propertyNames = event.getPersister().getPropertyNames();
            Object[] currentState = event.getState();
            Object[] previousState = event.getOldState();
            List<AuditTrailDto> auditTrailDTOList = new ArrayList<>();
            AuditLogService auditLogService = (AuditLogService) ApplicationContextProvider.getApplicationContext().getBean("auditLogService");
            for (int i = 0; i < currentState.length; i++) {
                if(currentState[i] != null && previousState[i] != null){
                if (!previousState[i].equals(currentState[i])) {
                       System.out.println("Inside On Flush Dirty   ************    **************      ==>>    " + propertyNames[i]);
                       auditTrailDTOList.add(new AuditTrailDto(entity.getClass().getCanonicalName(), event.getId().toString(), BlogENUM.AuditEvent.UPDATE.name(), propertyNames[i], previousState[i].toString(), currentState[i].toString()));
                }
                }
            }
            auditTrailDTOList.forEach(auditLogService::save);
        }
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        Object entity = event.getEntity();
        if (entity instanceof AuditAware) {
            String[] propertyNames = event.getPersister().getPropertyNames();
            Object[] state = event.getDeletedState();
            List<AuditTrailDto> auditTrailDTOList = new ArrayList<>();
            AuditLogService auditLogService = (AuditLogService) ApplicationContextProvider.getApplicationContext().getBean("auditLogService");
            for (int i = 0; i < propertyNames.length; i++) {
                if(state[i] != null){
                    System.out.println("Inside On Delete   ************    ************** ===>>>      " + propertyNames[i]);
                    auditTrailDTOList.add(new AuditTrailDto(entity.getClass().getCanonicalName(), event.getId().toString(), BlogENUM.AuditEvent.DELETE.name(), propertyNames[i], state[i].toString(), null));

                }
            }
            auditTrailDTOList.forEach(auditLogService::save);
        }
    }
}
