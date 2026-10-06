package com.itheima;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.exception.BusinessException;
import com.itheima.mapper.ClueMapper;
import com.itheima.model.req.CluePageReq;
import com.itheima.model.req.CluePoolReq;
import com.itheima.model.resp.CluePageResp;
import com.itheima.model.resp.CluePoolResp;
import com.itheima.service.ClueService;
import com.itheima.utils.UserHolder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 线索分页查询（GET /clues）回归测试。
 */
@SpringBootTest
public class ClueQueryPageListTest {

    private static final Integer OTHER_USER_ID = 3;

    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private ClueService clueService;

    //专员查询：SQL 必须按归属人 userId 过滤（曾把取值写成 #{req.userId}，CluePageReq 没有该属性而抛 ReflectionException）
    @Test
    public void testQueryPageListWithUserId() {
        Page<CluePageResp> page = clueMapper.queryPageList(new Page<>(1, 10), OTHER_USER_ID, new CluePageReq());

        assertNotNull(page);
        assertTrue(page.getRecords().stream().allMatch(row -> OTHER_USER_ID.equals(row.getUserId())),
                "返回的记录里包含了不属于该归属人的线索");
    }

    //管理员查询：不传归属人，返回全部有效线索
    @Test
    public void testQueryPageListWithoutUserId() {
        Page<CluePageResp> page = clueMapper.queryPageList(new Page<>(1, 10), null, new CluePageReq());

        assertNotNull(page);
    }

    //登录用户已被删除时，应给出业务提示而不是空指针
    @Test
    public void testQueryPageListWhenUserNotExist() {
        UserHolder.setUserId(-1);
        try {
            assertThrows(BusinessException.class, () -> clueService.queryPageList(new CluePageReq()));
        } finally {
            UserHolder.removeUserId();
        }
    }

    //线索池：查询可正常执行，且返回的记录状态一致
    //（线索池到底放“待分配(1)”还是“伪线索(4)”取决于需求，见 README 已知问题 1，这里只锁定“不会混入多种状态”）
    @Test
    public void testQueryPoolList() {
        Page<CluePoolResp> page = clueMapper.queryPoolList(new Page<>(1, 10), new CluePoolReq());

        assertNotNull(page);
        assertTrue(page.getRecords().stream().map(CluePoolResp::getStatus).distinct().count() <= 1,
                "线索池里出现了状态不一致的线索");
    }

    //线索池：按线索ID过滤
    @Test
    public void testQueryPoolListByClueId() {
        CluePoolReq req = new CluePoolReq();
        req.setClueId(-1);

        Page<CluePoolResp> page = clueMapper.queryPoolList(new Page<>(1, 10), req);

        assertEquals(0, page.getTotal());
    }
}
