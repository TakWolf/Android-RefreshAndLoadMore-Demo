# Android - RefreshAndLoadMore Demo

[![Android](https://img.shields.io/badge/android-23%2B-brightgreen)](https://developer.android.com)

一种简单且优雅的下拉刷新和加载更多分页的解决方案。

应用架构符合 [Jetpack](https://developer.android.com/jetpack/getting-started) 。

无边框模式基于 [InsetsWidget](https://github.com/TakWolf/Android-InsetsWidget) 方案。

下拉刷新基于 [SwipeRefreshLayout](https://developer.android.com/jetpack/androidx/releases/swiperefreshlayout) 实现。

加载更多基于 [HeaderAndFooterRecyclerView](https://github.com/TakWolf/Android-HeaderAndFooterRecyclerView) 中的 `LoadMoreFooter` 实现。

加载更多支持预载。并且数据填充不足一屏的情况，加载更多会自动触发。

具体思路和实现细节请参考程序。

![Demo](https://github.com/TakWolf/static.takwolf.com/blob/master/www/github/Android-RefreshAndLoadMore-Demo/01.gif)

## License

[Apache License 2.0](LICENSE)
