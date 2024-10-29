/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 *
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

package com.sakura.job.service.impl;

import com.sakura.job.api.JobApi;
import com.sakura.job.api.JobClient;
import com.sakura.job.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sakura.job.model.query.JobQuery;
import com.sakura.job.model.req.JobReq;
import com.sakura.job.model.req.JobStatusReq;
import com.sakura.job.model.resp.JobResp;
import com.sakura.starter.extension.crud.model.resp.PageResp;

import java.util.Collections;
import java.util.List;

/**
 * 任务业务实现
 *
 * @author KAI
 * @author hagyao520
 * @since 2024/6/25 17:25
 */
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobClient jobClient;
    private final JobApi jobApi;

    @Override
    public PageResp<JobResp> page(JobQuery query) {
        return jobClient.requestPage(() -> jobApi.page(query.getGroupName(), query.getJobName(), query
            .getJobStatus() != null ? query.getJobStatus().getValue() : null, query.getPage(), query.getSize()));
    }

    @Override
    public boolean add(JobReq req) {
        return Boolean.TRUE.equals(jobClient.request(() -> jobApi.add(req)));
    }

    @Override
    public boolean update(JobReq req, Long id) {
        req.setId(id);
        return Boolean.TRUE.equals(jobClient.request(() -> jobApi.update(req)));
    }

    @Override
    public boolean updateStatus(JobStatusReq req, Long id) {
        req.setId(id);
        return Boolean.TRUE.equals(jobClient.request(() -> jobApi.updateStatus(req)));
    }

    @Override
    public boolean delete(Long id) {
        return Boolean.TRUE.equals(jobClient.request(() -> jobApi.delete(Collections.singleton(id))));
    }

    @Override
    public boolean trigger(Long id) {
        return Boolean.TRUE.equals(jobClient.request(() -> jobApi.trigger(id)));
    }

    @Override
    public List<String> listGroup() {
        return jobClient.request(jobApi::listGroup);
    }
}