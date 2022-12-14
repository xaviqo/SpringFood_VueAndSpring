package xavi.tech.springfood.utils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;


public class OrderIdGenerator extends SequenceStyleGenerator {
	 
    public static final String VALUE_PREFIX_PARAMETER = "valuePrefix";
    public static final String VALUE_PREFIX_DEFAULT = "";
    private static final DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
    private String valuePrefix;
 
    @Override
    public Serializable generate(SharedSessionContractImplementor session,
            Object object) throws HibernateException {
        return valuePrefix + String.format(UUID.randomUUID().toString().substring(0, 14).replace("-", "").toUpperCase()
        		+"_"+dateFormat.format(new Date()), 
        		super.generate(session, object));
    }
 
    @Override
    public void configure(Type type, Properties params,
            ServiceRegistry serviceRegistry) throws MappingException {
        super.configure(LongType.INSTANCE, params, serviceRegistry);
        valuePrefix = ConfigurationHelper.getString(VALUE_PREFIX_PARAMETER,
                params, VALUE_PREFIX_DEFAULT);
    }
}

