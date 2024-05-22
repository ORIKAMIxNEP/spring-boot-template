package com.spring_boot_template.infrastructure.record;

import com.spring_boot_template.domain.record.Record;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface RecordMapper {
  // レコード追加
  @Insert(
      """
      INSERT INTO
        records(
          column1, column2
        )
      VALUES(
        #{record.column1()}, #{record.column2()}
      )
      """)
  void addRecord(final Record record);

  // レコード取得
  @Select(
      """
      SELECT
        *
      FROM
        records
      WHERE
        record_id = #{record.recordId()}
      """)
  @Results(
      id = "Record",
      value = {
        @Result(column = "record_id", property = "recordId"),
        @Result(column = "column1", property = "column1"),
        @Result(column = "column2", property = "column2")
      })
  Record fetchRecord(final Record record);

  // レコード更新
  @Update(
      """
      UPDATE
        records
      SET
        column1 = #{record.column1()},
        column2 = #{record.column2()}
      WHERE
        record_id = #{record.recordId()}
      """)
  void updateRecord(final Record record);

  // レコードカラム1更新
  @Update(
      """
      UPDATE
        records
      SET
        column1 = #{record.column1()}
      WHERE
        record_id = #{record.recordId()}
      """)
  void updateRecordColumn1(final Record record);

  // レコード削除
  @Delete(
      """
      DELETE FROM
        records
      WHERE
        record_id = #{record.recordId()}
      """)
  void deleteRecord(final Record record);
}