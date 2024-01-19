ALTER TABLE clinic_environment_inspections
    DROP FOREIGN KEY FK_CLINIC_ENVIRONMENT_INSPECTIONS_ON_EQUIPMENT;

ALTER TABLE cv_radiations
    DROP FOREIGN KEY FK_CV_RADIATIONS_ON_EQUIPMENT;

ALTER TABLE external_quality_assessments
    DROP FOREIGN KEY FK_EXTERNAL_QUALITY_ASSESSMENTS_ON_EQUIPMENT;

ALTER TABLE radiation_inspections
    DROP FOREIGN KEY FK_RADIATION_INSPECTIONS_ON_EQUIPMENT;

DROP TABLE clinic_environment_inspections;

DROP TABLE cv_radiations;

DROP TABLE external_quality_assessments;

DROP TABLE radiation_inspections;

ALTER TABLE equipments
    DROP COLUMN regular_clinic_environment_inspection;

ALTER TABLE equipments
    DROP COLUMN regular_cv_radiation;

ALTER TABLE equipments
    DROP COLUMN regular_external_quality_assessment;

ALTER TABLE equipments
    DROP COLUMN regular_radiation_inspection;