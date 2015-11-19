#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__='perl生成的title和mongodb的字段对应关系'
__author__='lin'

pgs = {"*SD": "sd",
       "Map_Ratio(%)": "mapRatio",
       "MT_ratio(%)": "mtRatio",
       "no_enough_reads": "noEnoughReads",
       "Total_Reads": "totalReads",
       "gc_Chromosome_DensityPng": "gcChromosomeDensityPng",
       "Duplicate(%)": "duplicate",
       "Duplicate": "duplicate",
       "Map_Reads": "mapReads",
       "win_size": "winSize",
       "gc_GC_Counts_NormalizedPng": "gcCountsNormalizedPng",
       "GC_Count(%)": "gcCount",
       "gc_GC_Counts_RawPng": "gcCountsRawPng",
       "splitPng": "splitPng",
       "testPng": "testPng",
       "miniPng": "miniPng",
       "finalPng": "finalPng",
       "note": "note",
       "detail": "detail",
       "report": "report"
       }
nipt = {"Total_Reads": "totalReads",
       "Reads_Length>50bp": "readsLength",
       "Mapping_Reads": "mappingReads",
       "readsPercent": "readsPercent",
       "mappingPercent": "mappingPercent"
       }

hcvmodel = {
       'File_Name':'fileName',
       'File Name':'fileName',
       'Subtype':'subtype',
       'Subject_Name':'subjectName',
       'Subject Name':'subjectName',
       'Identity':'identity',
       'Overlap/total':'total',
       'E_value':'evalue',
       'Score':'score'
       }

if __name__ == '__main__':
       print hcvmodel['File Name']