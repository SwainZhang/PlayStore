package domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author  Administrator
 * @time 	2015-7-16 下午2:03:28
 * @des	TODO
 *
 * @version $Rev: 20 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-16 14:38:48 +0800 (星期四, 16 七月 2015) $
 * @updateDes TODO
 */
public class HomeBean implements Serializable{
	public List<AppInfoBean>	list;
	public List<String>			picture;
}
