package com.sakura.test.domain;

import com.sakura.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 测试管理-测试度量实体对象
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2024-01-24
 */
@Data
public class TestMetric implements Serializable {
    private UI ui;

    @Data
    public static class CreatedInCustom {
        private int week;
        private int month;
        private int year;
    }

    @Data
    public static class UI {
        private String projectId;
        private String versionId;
        private String parentId;

        private FunctionalModule FunctionalModule;
        private AutomationScene AutomationScene;
        private AutomationExecute AutomationExecute;

        @Data
        public static class SeriesData {
            private String name;
            private int total;
            //            private int createdInCustom;
            private CreatedInCustom createdInCustom;
        }

        @Data
        public static class FunctionalModule {
            private String name = "模块数量";
            private int totalAll;
            //        private int createdInCustomAll;
            private CreatedInCustom createdInCustomAll;

            private SeriesData seriesData;
            private List<SeriesData> seriesDataList;
        }

        @Data
        public static class AutomationScene {
            private String name = "场景数量";;
            private int totalAll;
            //        private int createdInCustomAll;
            private CreatedInCustom createdInCustomAll;

            private SeriesData seriesData;
            private List<SeriesData> seriesDataList;
        }

        @Data
        public static class AutomationExecute {
            private RunInCustom runInCustom;
            private List<RunInCustom> runInCustomList;
            private ExeInCustom exeInCustom;
            private List<ExeInCustom> exeInCustomList;
            private DefectCustom defectCustom;
            private List<DefectCustom> defectCustomList;
            private LabInCustom labInCustom;
            private List<LabInCustom> labInCustomList;
            private RateInCustom rateInCustom;
            private List<RateInCustom> rateInCustomList;
            @Data
            public static class RunInCustom {
                private String name;
                private int total;
            }
            @Data
            public static class ExeInCustom {
                private String name;
                private int total;
            }
            @Data
            public static class DefectCustom {
                private String name;
                private int total;
            }
            @Data
            public static class LabInCustom {
                private String name;
                private String total;
            }
            @Data
            public static class RateInCustom {
                private String name;
                private String coverRate;
                private String passeRate;
                private String executeRate;
                private String defectRate;
            }
        }
    }
}