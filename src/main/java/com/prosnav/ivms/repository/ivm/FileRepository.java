/**
 * 
 */
package com.prosnav.ivms.repository.ivm;

import com.prosnav.ivms.core.BaseMongoRepository;
import com.prosnav.ivms.model.FileModel;

/**
 * @author wangnan
 *
 */
public interface FileRepository extends BaseMongoRepository<FileModel, Long>, FileRepositoryCustom {

}
