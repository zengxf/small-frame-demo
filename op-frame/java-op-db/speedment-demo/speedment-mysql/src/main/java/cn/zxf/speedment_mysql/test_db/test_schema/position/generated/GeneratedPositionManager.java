package cn.zxf.speedment_mysql.test_db.test_schema.position.generated;

import cn.zxf.speedment_mysql.test_db.test_schema.position.Position;
import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.core.manager.Manager;

/**
 * The generated base interface for the manager of every {@link
 * cn.zxf.speedment_mysql.test_db.test_schema.position.Position} entity.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@GeneratedCode("Speedment")
public interface GeneratedPositionManager extends Manager<Position> {
    
    @Override
    default Class<Position> getEntityClass() {
        return Position.class;
    }
}