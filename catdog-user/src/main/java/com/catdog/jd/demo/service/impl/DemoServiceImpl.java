package com.catdog.jd.demo.service.impl;

import com.catdog.jd.demo.entity.TestTable;
import com.catdog.jd.demo.mapper.DemoMapper;
import com.catdog.jd.demo.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DemoServiceImpl implements DemoService{

	private final static Logger LOGGER = LoggerFactory.getLogger(DemoServiceImpl.class);

	@Autowired
	private DemoMapper demoMapper;

	private AtomicInteger atomicInteger = new AtomicInteger();

	private final static Integer MAX_COUNT = 1000 * 1000 * 5;

	@Override
	public Map<String,Object> get() {
		return demoMapper.get();
	}
	volatile char[] chars = new char[]{'q','w','e','r','s','f','g','h'};
	@Override
	public void add() {
//		long startTime = System.currentTimeMillis();
//
//		ExecutorService service = Executors.newFixedThreadPool(15);
//		for ( int j = 0; j < MAX_COUNT; j++) {
//			int k = j;
//			service.submit(() ->  {
//				LOGGER.info("-----------------------------------------{}",k);
//				atomicInteger.set(k);
//				TestTable testTable = new TestTable();
////				testTable.setId(atomicInteger.get());
//				testTable.setName(chars[k % chars.length] +"-" + k + System.currentTimeMillis());
//				testTable.setPwd(atomicInteger.get() + "-"+ System.currentTimeMillis());
//				testTable.setDate(new Date());
//				demoMapper.add(testTable);
//				LOGGER.info(testTable.toString());
//			});
////			TestTable testTable = new TestTable();
//////				testTable.setId(atomicInteger.get());
////			testTable.setName(chars[k % chars.length] +"-" + k);
////			testTable.setPwd(k +"");
////			testTable.setDate(new Date());
////			demoMapper.add(testTable);
////			LOGGER.info(testTable.toString());
//		}
//		service.shutdown();
//
//		long endTime = System.currentTimeMillis();
//		LOGGER.info("50万跳数据所用时间：" + (endTime - startTime));
		try {
			add2();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	public void add2() throws InterruptedException {

		AtomicInteger ai = new AtomicInteger();
		AtomicLong datetime = new AtomicLong();
		ThreadLocal<ArrayList<TestTable>> threadLocal = new ThreadLocal<>();
		ExecutorService executor = Executors.newFixedThreadPool(10);
		for (int i = 0; i< 500; i++){
			executor.execute(new Runnable() {
				@Override
				public void run() {
					long startTime = System.currentTimeMillis();
					ArrayList<TestTable> arrayList = new ArrayList<>();
					for (int j = 0; j< 10000; j++){
						String value = ai.addAndGet(1) + "-" + System.currentTimeMillis();
						Date date = new Date();
						TestTable testTable = new TestTable();
						testTable.setName(value);
						testTable.setPwd(value);
						testTable.setDate(date);
						arrayList.add(testTable);
						if(threadLocal.get() == null){
							threadLocal.set(arrayList);
						}
					}
					demoMapper.add2(arrayList);
					long endTime = System.currentTimeMillis();
					datetime.addAndGet(endTime - startTime);
				}
			});
		}
		executor.shutdown();

		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					LOGGER.info("一共花费的时间为：" + datetime.get()/1000 +"s");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
