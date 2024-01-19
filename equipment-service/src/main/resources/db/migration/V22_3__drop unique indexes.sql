ALTER TABLE supplies
    MODIFY year_in_use INT NULL;

ALTER TABLE supplies
    MODIFY year_of_manufacture INT NULL;

ALTER TABLE supplies
    DROP KEY code;

ALTER TABLE supplies
    DROP KEY hashCode;