import React, { Component } from "react";
import {
  Button,
  UncontrolledDropdown,
  DropdownMenu,
  DropdownToggle,
  DropdownItem,
  Input,
} from "reactstrap";
import DataTable from "react-data-table-component";
import classnames from "classnames";
import ReactPaginate from "react-paginate";
import { history } from "../../../history";
import {
  Edit,
  Trash,
  ChevronDown,
  Plus,
  Check,
  ChevronLeft,
  ChevronRight,
} from "react-feather";
import { connect } from "react-redux";
import Sidebar from "./DataSideBar";
import Checkbox from "../../../components/@vuexy/checkbox/CheckboxesVuexy";
import "../../../assets/scss/plugins/extensions/react-paginate.scss";
import "../../../assets/scss/pages/data-list.scss";
import {
  getData,
  getInitialData,
  filterData,
} from "../../../redux/actions/category/Category";
const selectedStyle = {
  rows: {
    selectedHighlighStyle: {
      backgroundColor: "rgba(115,103,240,.05)",
      color: "#7367F0 !important",
      boxShadow: "0 0 1px 0 #7367F0 !important",
      "&:hover": {
        transform: "translateY(0px) !important",
      },
    },
  },
};
const ActionsComponent = (props) => {
  return (
    <div className="data-list-action">
      <Edit
        className="cursor-pointer mr-1"
        size={20}
        onClick={() => {
          return props.currentData(props.row);
        }}
      />
      <Trash
        className="cursor-pointer"
        size={20}
        onClick={() => {
          props.deleteRow(props.row);
        }}
      />
    </div>
  );
};
const SubCategoryComponent = (props) => {
  if (props.row.subCategories.length > 0) {
    let subCategories = props.row.subCategories;
    return subCategories.map((rs) => (
      <p
        title={rs.name}
        key={props.id}
        className="text-truncate text-bold-500 mb-0"
      >
        {rs.name}
      </p>
    ));
  }
  return null;
};
const CustomHeader = (props) => {
  return (
    <div className="data-list-header d-flex justify-content-between flex-wrap">
      <div className="actions-left d-flex flex-wrap">
        <UncontrolledDropdown className="data-list-dropdown mr-1">
          <DropdownToggle className="p-1" color="primary">
            <span className="align-middle mr-1">Actions</span>
            <ChevronDown size={15} />
          </DropdownToggle>
          <DropdownMenu tag="div" right>
            <DropdownItem tag="a" onClick={() => props.handleDeleteSeclect(1)}>
              Delete
            </DropdownItem>
            <DropdownItem tag="a">Archive</DropdownItem>
            <DropdownItem tag="a">Print</DropdownItem>
            <DropdownItem tag="a">Export</DropdownItem>
          </DropdownMenu>
        </UncontrolledDropdown>
        <Button
          className="add-new-btn"
          color="primary"
          onClick={() => props.handleSidebar(true, true)}
          outline
        >
          <Plus size={15} />
          <span className="align-middle">Thêm mới</span>
        </Button>
      </div>
      <div className="actions-right d-flex flex-wrap mt-sm-0 mt-2">
        <UncontrolledDropdown className="data-list-rows-dropdown mr-1 d-md-block d-none">
          <DropdownToggle color="" className="sort-dropdown">
            <span className="align-middle mx-50">
              {`${props.index[0]} - ${props.index[1]} của ${props.total}`}
            </span>
            <ChevronDown size={15} />
          </DropdownToggle>
          <DropdownMenu tag="div" right>
            <DropdownItem tag="a" onClick={() => props.handleRowsPerPage(10)}>
              10
            </DropdownItem>
            <DropdownItem tag="a" onClick={() => props.handleRowsPerPage(15)}>
              15
            </DropdownItem>
            <DropdownItem tag="a" onClick={() => props.handleRowsPerPage(20)}>
              20
            </DropdownItem>
          </DropdownMenu>
        </UncontrolledDropdown>
        <div className="filter-section">
          <Input type="text" onChange={(e) => props.handleFilter(e)} />
        </div>
      </div>
    </div>
  );
};

class DataCategoryConfig extends Component {
  static getDerivedStateFromProps(props, state) {
    if (
      props.dataList.data.length !== state.data.length ||
      state.currentPage !== props.parsedFilter.page
    ) {
      return {
        data: props.dataList.data,
        allData: props.dataList.filteredData,
        totalPages: props.dataList.totalPages,
        currentPage: parseInt(props.parsedFilter.page) - 1,
        rowsPerPage: parseInt(props.parsedFilter.perPage),
        totalRecords: props.dataList.totalRecords,
        sortIndex: props.dataList.sortIndex,
      };
    }

    // Return null if the state hasn't changed
    return null;
  }

  componentDidMount() {
    this.props.getData(this.props.parsedFilter);
    this.props.getInitialData();
  }

  state = {
    data: [],
    totalPages: 0,
    currentPage: 0,
    columns: [
      {
        name: "ID",
        selector: "id",
        sortable: true,
      },
      {
        name: "Name",
        selector: "name",
        sortable: true,
        minWidth: "300px",
        cell: (row) => (
          <p title={row.name} className="text-truncate text-bold-500 mb-0">
            {row.name}
          </p>
        ),
      },
      {
        name: "Description",
        selector: "description",
        sortable: true,
      },

      {
        name: "Slug",
        selector: "slug",
        sortable: true,
      },

      {
        name: "Actions",
        sortable: true,
        cell: (row) => (
          <ActionsComponent
            row={row}
            getData={this.props.getData}
            parsedFilter={this.props.parsedFilter}
            currentData={this.handleCurrentData}
            deleteRow={this.handleDelete}
          />
        ),
      },
      {
        name: "Thể loại con",
        selector: "subCategories",
        minWidth: "300px",
        cell: (row) => <SubCategoryComponent row={row} />,
      },
    ],
    allData: [],
    value: "",
    rowsPerPage: 10,
    sidebar: false,
    currentData: null,
    selected: [],
    totalRecords: 0,
    sortIndex: [],
    addNew: "",
  };
  handleRowsPerPage = (value) => {
    let { parsedFilter, getData } = this.props;
    let page = parsedFilter.page !== undefined ? parsedFilter.page : 1;
    history.push(`categories?page=${page}&perPage=${value}`);
    this.setState({ rowsPerPage: value });
    getData({ page: parsedFilter.page, perPage: value });
  };
  handleSidebar = (boolean, addNew = false) => {
    this.setState({ sidebar: boolean });
    if (addNew === true) this.setState({ currentData: null, addNew: true });
  };
  handlePagination = (page) => {
    let { parsedFilter, getData } = this.props;
    let perPage =
      parsedFilter.perPage !== undefined ? parsedFilter.perPage : 10;

    history.push(`categories?page=${page.selected + 1}&perPage=${perPage}`);
    getData({ page: page.selected + 1, perPage: perPage });
    this.setState({ currentPage: page.selected });
  };
  handleSidebar = (boolean, addNew = false) => {
    this.setState({ sidebar: boolean });
    if (addNew === true) this.setState({ currentData: null, addNew: true });
  };
  handleDeleteSeclect = () => {
    console.log(this.state.selected);
  };
  handleFilter = (e) => {
    this.setState({ value: e.target.value });
    this.props.filterData(e.target.value);
  };
  handleCurrentData = (obj) => {
    this.setState({ currentData: obj });
    this.handleSidebar(true);
  };

  render() {
    let {
      columns,
      data,
      allData,
      totalPages,
      value,
      rowsPerPage,
      currentData,
      sidebar,
      totalRecords,
      sortIndex,
    } = this.state;
    return (
      <div className="data-list list-view">
        <DataTable
          columns={columns}
          data={value.length ? allData : data}
          pagination
          paginationServer
          paginationComponent={() => (
            <ReactPaginate
              previousLabel={<ChevronLeft size={15} />}
              nextLabel={<ChevronRight size={15} />}
              breakLabel="..."
              breakClassName="break-me"
              pageCount={totalPages}
              containerClassName="vx-pagination separated-pagination pagination-end pagination-sm mb-0 mt-2"
              activeClassName="active"
              forcePage={
                this.props.parsedFilter.page
                  ? parseInt(this.props.parsedFilter.page - 1)
                  : 0
              }
              onPageChange={(page) => this.handlePagination(page)}
            />
          )}
          noHeader
          subHeader
          selectableRows
          responsive
          pointerOnHover
          selectableRowsHighlight
          onSelectedRowsChange={(data) =>
            this.setState({ selected: data.selectedRows })
          }
          customStyles={selectedStyle}
          subHeaderComponent={
            <CustomHeader
              handleSidebar={this.handleSidebar}
              handleFilter={this.handleFilter}
              handleRowsPerPage={this.handleRowsPerPage}
              rowsPerPage={rowsPerPage}
              total={totalRecords}
              index={sortIndex}
              handleDeleteSeclect={this.handleDeleteSeclect}
            />
          }
          sortIcon={<ChevronDown />}
          selectableRowsComponent={Checkbox}
          selectableRowsComponentProps={{
            color: "primary",
            icon: <Check className="vx-icon" size={12} />,
            label: "",
            size: "sm",
          }}
        />
        <Sidebar
          show={sidebar}
          data={currentData}
          updateData={this.props.updateData}
          addData={this.props.addData}
          handleSidebar={this.handleSidebar}
          thumbView={this.props.thumbView}
          getData={this.props.getData}
          dataParams={this.props.parsedFilter}
          addNew={this.state.addNew}
        />
        <div
          className={classnames("data-list-overlay", {
            show: sidebar,
          })}
          onClick={() => this.handleSidebar(false, true)}
        />
      </div>
    );
  }
}
const mapStateToProps = (state) => {
  return {
    dataList: state.category,
  };
};

export default connect(mapStateToProps, {
  getData,
  getInitialData,
  filterData,
})(DataCategoryConfig);
