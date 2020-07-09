package top.yulegou.zeus.controller;/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.yulegou.zeus.config.ListData;
import top.yulegou.zeus.constant.ErrorCode;
import top.yulegou.zeus.dao.domain.Result;
import top.yulegou.zeus.dao.domain.ZTask;
import top.yulegou.zeus.dao.domain.ZTaskCollected;
import top.yulegou.zeus.dao.domain.ZTaskCollectedExample;
import top.yulegou.zeus.domain.CollectedDTO;
import top.yulegou.zeus.manager.ZeusCollectedManager;
import top.yulegou.zeus.manager.ZeusTaskManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author irisroyalty
 * @date 2020/7/9
 **/
@Controller
@RequestMapping("/collected")
public class CollectedController extends BaseController {
    @Autowired
    ZeusCollectedManager zeusCollectedManager;
    @Autowired
    ZeusTaskManager zeusTaskManager;

    @GetMapping("/list")
    public String showCollected(Model model) {
        return "collected/list";
    }

    @GetMapping("/list.data")
    @ResponseBody
    public String getCollectedData(
            @RequestParam(value = "page", defaultValue = "1") Integer start,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder) {
        JSONObject object = new JSONObject();
        int off = (start - 1) * size;
        off = Math.max(0, off);
        ZTaskCollectedExample example = new ZTaskCollectedExample();
        example.setLimit(size);
        example.setOffset(off);
        example.setOrderByClause(" id " + sortOrder);
        List<ZTaskCollected> collecteds = zeusCollectedManager.selectCollected(example);
        collecteds.stream().forEach(collected -> {
            ZTask task = zeusTaskManager.getTaskById(collected.getTaskId());
            if (task == null) {
            } else {
                collected.setTaskName(task.gettName());
            }
        });
        object.put("rows", collecteds);
        object.put("total", zeusCollectedManager.countCollected());
        return object.toJSONString();
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public String deleteCollected(@ListData ArrayList<Integer> selectedIds) {
        if (selectedIds != null && !selectedIds.isEmpty()) {
            int r = zeusCollectedManager.deleteByIds(selectedIds);
            if (r > 0) {
                return Result.success(r);
            } else {
                return Result.failed(ErrorCode.SYSTEM_ERROR.getCode(), "删除失败，请刷新后重试");
            }
        } else {
            return Result.failed(ErrorCode.REQUEST_PARAM_ERROR.getCode(), "参数错误");
        }
    }
}
