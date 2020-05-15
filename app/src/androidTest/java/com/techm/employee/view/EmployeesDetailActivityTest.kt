package com.techm.employee.view

import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.techm.employee.R
import com.telstra.mvvmdemo.utils.ToastMatcher
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumentation Test for EmployeeDetailsActivity
 */
@RunWith(AndroidJUnit4::class)
class EmployeesDetailActivityTest {

    /** Define ActivityTestRule */
    @get:Rule
    var employeeDetailActivityRule: ActivityTestRule<EmployeeDetailsActivity> =
        ActivityTestRule(EmployeeDetailsActivity::class.java)
    var mActivity: EmployeeDetailsActivity? = null

    /** Function to test App successfully launch */
    @Test
    fun appLaunchSuccessfully() {
        ActivityScenario.launch(EmployeeDetailsActivity::class.java)
    }

    /** Function to test ActionBarTitle Showing or not */
    @Test
    fun onLaunchActionBarTitleIsDisplayed() {
        val actionBar: ActionBar? = employeeDetailActivityRule.activity.supportActionBar
        Assert.assertNotNull(actionBar?.title)
    }

    /** Function to test Back Press button*/
    @Test
    fun click_backPress() {
        onView(isRoot()).perform(pressBackUnconditionally());
    }

    /** Function to test ProgressBar Displaying or not */
    @Test
    fun onLaunchCheckProgressBarIsDisplayed() {
        IdlingResource.ResourceCallback {
            onView(withId(R.id.progressBar))
                .check(matches(isDisplayed()))
        }
    }

    /** Function to test pull to refresh */
    @Test
    fun testPullToRefresh_swipeDownToPullRefresh() {
        onView(withId(R.id.swipeRefreshLayout)).perform(swipeDown())
    }

    /** Function to test RecyclerView SwipeUp */
    @Test
    fun testSwipeUp_swipeUpRecyclerView() {
        onView(withId(R.id.recycler_view)).perform(swipeUp())
    }

    /** Function to test RecyclerView Scrolling Position */
    @Test
    fun testScrolling_scrollToPosition() {
        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(100))
    }

    /** Function to test EmployeeDetails Displaying in RecyclerView or not */
    @Test
    fun testFactsItemsList_EmployeesItemsListIsDisplayed() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

    /** Function to test Toast Message is showing or not */
    @Test
    fun noInternetToastMessageTest() {
        onView(withText(R.string.noconnectivity)).inRoot(ToastMatcher())
            .check(matches((isDisplayed())))
    }

    /** Function to test RecyclerView Item Click Functionality */
    @Test
    fun testRecyclerview_item_click() {
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click())
        )
    }

    /** Function to test RecyclerView Title */
    @Test
    fun testRecyclerview_EmployeeName_hasText() {
        onView(withId(R.id.recycler_view))
            .check(matches(hasDescendant(withText("Tiger Nixon"))));
    }

    /** Function to test RecyclerView Title Negative Scenario*/
    @Test
    fun testRecyclerview_EmployeeName_hasNoText() {
        onView(withId(R.id.recycler_view))
            .check(matches(not(hasDescendant(withText("Himanshu")))));
    }

    /** Function to test Back Press button from RecyclerView Screen */
    @Test
    fun itemDisplayed_backPress() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        Espresso.pressBackUnconditionally()
    }

    @Test
    fun onLaunchCheckRecyclerViewIsDisplayed() {
        Thread.sleep(3000)
        val recyclerView: RecyclerView =
            employeeDetailActivityRule.activity.findViewById(R.id.recycler_view)
        onView(withId(R.id.recycler_view)).check(
            matches(
                isDisplayed()
            )
        )
        Assert.assertNotSame(0, recyclerView.adapter?.itemCount)
    }

    @Test
    fun testAddMenuButtonIsDisplayed() {
        onView(withId(R.id.add_user)).perform(click())
        onView(withId(R.id.tvaddemployee)).check(matches(isDisplayed()))
           }
    @After
    fun testDone() {
        mActivity = null
    }
}