UPDATE equipments
SET regular_clinic_environment_inspection = '0'
WHERE regular_clinic_environment_inspection IS NULL;
ALTER TABLE equipments
    MODIFY regular_clinic_environment_inspection INT DEFAULT 0 NOT NULL;

UPDATE equipments
SET regular_cv_radiation = '0'
WHERE regular_cv_radiation IS NULL;
ALTER TABLE equipments
    MODIFY regular_cv_radiation INT DEFAULT 0 NOT NULL;

UPDATE equipments
SET regular_external_quality_assessment = '0'
WHERE regular_external_quality_assessment IS NULL;
ALTER TABLE equipments
    MODIFY regular_external_quality_assessment INT DEFAULT 0 NOT NULL;

UPDATE equipments
SET regular_inspection = '0'
WHERE regular_inspection IS NULL;
ALTER TABLE equipments
    MODIFY regular_inspection INT DEFAULT 0 NOT NULL;

UPDATE equipments
SET regular_maintenance = '0'
WHERE regular_maintenance IS NULL;
ALTER TABLE equipments
    MODIFY regular_maintenance INT DEFAULT 0 NOT NULL;

UPDATE equipments
SET regular_radiation_inspection = '0'
WHERE regular_radiation_inspection IS NULL;
ALTER TABLE equipments
    MODIFY regular_radiation_inspection INT DEFAULT 0 NOT NULL;