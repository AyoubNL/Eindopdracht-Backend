package nl.novi.backend_it_helpdesk.config;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.hibernate.type.descriptor.java.spi.JavaTypeBasicAdaptor;
import org.hibernate.type.descriptor.jdbc.NumericJdbcType;
import org.hibernate.type.internal.NamedBasicTypeImpl;

import java.util.Properties;

public class CustomIdGenerator extends SequenceStyleGenerator {

    public final String INCREMENT_PARAM = "increment_size";

    public final int DEFAULT_INCREMENT_SIZE = 1;

    public final String VALUE_PARAM = "initial_value";

    public final int INITIAL_VALUE = 4;

    @Override
    public Object generate( SharedSessionContractImplementor session, Object object ) {

        return "CASE" + String.format( "%05d", super.generate( session, object ) );
    }

    @Override
    public void configure( Type type, Properties parameters, ServiceRegistry serviceRegistry ) throws MappingException {
        parameters.put( INCREMENT_PARAM, DEFAULT_INCREMENT_SIZE );
        parameters.put( VALUE_PARAM, INITIAL_VALUE );
        Type idType = new NamedBasicTypeImpl<>( new JavaTypeBasicAdaptor<>( Long.class ), NumericJdbcType.INSTANCE, "long" );
        super.configure( idType, parameters, serviceRegistry );
    }



}
