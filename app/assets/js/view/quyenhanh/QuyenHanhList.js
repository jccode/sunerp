/**
 * Created by dungvn3000 on 3/21/14.
 */
Ext.define('sunerp.view.quyenhanh.QuyenHanhList', {
    extend: 'sunerp.view.core.BaseListView',
    alias: 'widget.quyenHanhList',
    requires: [
        'sunerp.controller.quyenhanh.QuyenHanhListCtr',
        'sunerp.component.ChucVuCb'
    ],
    controller: 'sunerp.controller.quyenhanh.QuyenHanhListCtr',
    inject: ['quyenHanhStore'],
    config: {
        quyenHanhStore: null
    },
    searchField: 'domain',
    initComponent: function () {
        var me = this;
        me.store = this.getQuyenHanhStore();
        me.columns = [
            {xtype: 'rownumberer'},
            {header: 'Domain', dataIndex: 'domain', flex: 1},
            {header: 'Đọc', dataIndex: 'read', flex: 1, xtype: 'booleancolumn'},
            {header: 'Ghi', dataIndex: 'write', flex: 1, xtype: 'booleancolumn'},
            {header: 'Giới hạn', dataIndex: 'gioiHan', flex: 1},
            {header: 'Chức vụ', dataIndex: 'chucVu.name', flex: 1},
            {
                xtype: 'actioncolumn',
                header: 'Option',
                sortable: false,
                menuDisabled: true,
                items: [this.deleteBtn()]
            }
        ];
        me.callParent(arguments);
    },
    getTBar: function () {
        var tbar = this.callParent(arguments);
        tbar = Ext.Array.insert(tbar, 1, [
            {
                xtype: 'chucvucb',
                name: 'Chức vụ',
                width: 150
            }
        ]);
        return tbar;
    }
});