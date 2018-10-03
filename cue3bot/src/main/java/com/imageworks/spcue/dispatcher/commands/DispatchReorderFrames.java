
/*
 * Copyright (c) 2018 Sony Pictures Imageworks Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package com.imageworks.spcue.dispatcher.commands;

import com.imageworks.spcue.Job;
import com.imageworks.spcue.Layer;
import com.imageworks.spcue.CueIce.Order;
import com.imageworks.spcue.service.JobManagerSupport;
import com.imageworks.util.FileSequence.FrameSet;

public class DispatchReorderFrames implements Runnable {

    private Job job = null;
    private Layer layer = null;
    private FrameSet frameSet;
    private Order order;
    private JobManagerSupport jobManagerSupport;

    public DispatchReorderFrames(Job job, FrameSet frameSet, Order order, JobManagerSupport jobManagerSupport) {
        this.job = job;
        this.frameSet = frameSet;
        this.order = order;
        this.jobManagerSupport = jobManagerSupport;
    }

    public DispatchReorderFrames(Layer layer, FrameSet frameSet, Order order, JobManagerSupport jobManagerSupport) {
        this.layer = layer;
        this.frameSet = frameSet;
        this.order = order;
        this.jobManagerSupport = jobManagerSupport;
    }

    @Override
    public void run() {
        new DispatchCommandTemplate() {
            public void wrapDispatchCommand() {
                if (job != null) {
                    jobManagerSupport.reorderJob(job, frameSet, order);
                }
                else if (layer != null) {
                    jobManagerSupport.reorderLayer(layer, frameSet, order);
                }
            }
        }.execute();
    }
}
