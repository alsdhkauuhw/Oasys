package cn.linter.oasys.announcement.service.impl;

import cn.linter.oasys.announcement.dao.AnnouncementDao;
import cn.linter.oasys.announcement.entity.Announcement;
import cn.linter.oasys.announcement.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公告服务实现类
 *
 * @author wangxiaoyang
 * @since 2020/11/11
 */
@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementDao announcementDao;

    @Override
    public Announcement query(Long id) {
        return announcementDao.select(id);
    }

    @Override
    public List<Announcement> list() {
        return announcementDao.list();
    }

    @Override
    public Announcement create(Announcement announcement) {
        announcementDao.insert(announcement);
        return announcement;
    }

    @Override
    public Announcement update(Announcement announcement) {
        announcementDao.update(announcement);
        return query(announcement.getId());
    }

    @Override
    public boolean delete(Long id) {
        return announcementDao.delete(id) > 0;
    }

}