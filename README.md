# Android - RefreshAndLoadMore Demo

[![Platform](https://img.shields.io/badge/platform-Android-brightgreen)](https://developer.android.com)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/github/license/TakWolf/Android-RefreshAndLoadMore-Demo)](LICENSE)

一种简单且优雅的下拉刷新和加载更多分页的解决方案。

应用架构符合 [Jetpack](https://developer.android.com/jetpack/getting-started) 。

下拉刷新基于 [SwipeRefreshLayout](https://developer.android.com/jetpack/androidx/releases/swiperefreshlayout) 实现。

加载更多基于 [HeaderAndFooterRecyclerView](https://github.com/TakWolf/Android-HeaderAndFooterRecyclerView) 中的 `LoadMoreFooter` 实现。

适配了沉浸式导航栏，使用了 [InsetsWidget](https://github.com/TakWolf/Android-InsetsWidget) 方案。

加载更多支持预载。并且数据填充不足一屏的情况，加载更多会自动触发。

具体思路和实现细节请参考程序。

![Demo](https://github.com/TakWolf/static.takwolf.com/blob/master/www/github/Android-RefreshAndLoadMore-Demo/01.gif)

## License

[Apache License 2.0](LICENSE)
