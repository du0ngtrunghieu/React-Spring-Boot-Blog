package com.trunghieu.common.model.audit;

import com.trunghieu.api.audit.AuditLogService;
import com.trunghieu.common.dto.AuditTrailDto;
import com.trunghieu.common.util.ApplicationContextProvider;
import com.trunghieu.common.util.BlogENUM;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/6/2020.
 * Class: AuditLogInterceptor.java
 * By : Admin
 */
@Component
public class AuditLogInterceptor extends EmptyInterceptor {
    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException {
        if (entity instanceof AuditAware) {
            List<AuditTrailDto> auditTrailDTOList = new ArrayList<>();
            AuditLogService auditLogService = (AuditLogService) ApplicationContextProvider.getApplicationContext().getBean("auditLogService");
            for (int i = 0; i < currentState.length; i++) {
                if (!previousState[i].equals(currentState[i])) {
                    System.out.println("Inside On Flush Dirty   ************    **************      ==>>    " + propertyNames[i]);
                    auditTrailDTOList.add(new AuditTrailDto(entity.getClass().getCanonicalName(), id.toString(), BlogENUM.AuditEvent.UPDATE.name(), propertyNames[i], previousState[i].toString(), currentState[i].toString()));
                }
            }
            auditTrailDTOList.forEach(auditLogService::save);
        }
        return true;
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        if (entity instanceof AuditAware) {
            List<AuditTrailDto> auditTrailDTOList = new ArrayList<>();
            AuditLogService auditLogService = (AuditLogService) ApplicationContextProvider.getApplicationContext().getBean("auditLogService");
            for (int i = 0; i < propertyNames.length; i++) {
                System.out.println("Inside On Save   ************    ************** ===>>>      " + propertyNames[i]);
                auditTrailDTOList.add(new AuditTrailDto(entity.getClass().getCanonicalName(), id.toString(), BlogENUM.AuditEvent.INSERT.name(), propertyNames[i], null, state[i].toString()));
            }
            auditTrailDTOList.forEach(auditLogService::save);
        }
        return super.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        if (entity instanceof AuditAware) {
            List<AuditTrailDto> auditTrailDTOList = new ArrayList<>();
            AuditLogService auditLogService = (AuditLogService) ApplicationContextProvider.getApplicationContext().getBean("auditLogService");
            for (int i = 0; i < propertyNames.length; i++) {
                System.out.println("Inside On Delete   ************    ************** ===>>>      " + propertyNames[i]);
                auditTrailDTOList.add(new AuditTrailDto(entity.getClass().getCanonicalName(), id.toString(), BlogENUM.AuditEvent.DELETE.name(), propertyNames[i], state[i].toString(), null));
            }
            auditTrailDTOList.forEach(auditLogService::save);
        }
    }
}
